import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { Counter, Trend } from 'k6/metrics';

const URL_SHORTENER = 'http://localhost:8080/api/v1/url';
const USER_SERVICE = 'http://localhost:8081/api/v1/user';
const STATISTICS_SERVICE = 'http://localhost:8082/api/v1/statistics';

// Custom Metrics
const createdUsersCounter = new Counter('created_users');
const createdUrlsCounter = new Counter('created_urls');
const clicksCounter = new Counter('total_clicks');
const createUserDuration = new Trend('create_user_duration');
const createUrlDuration = new Trend('create_url_duration');
const clickDuration = new Trend('click_duration');

export const options = {
  vus: 1,
  iterations: 1,
  thresholds: {
    http_req_duration: ['p(95)<500'],
  },
};

// --- Helpers ---
function generateRandomEmail() {
  return `user_${Math.random().toString(36).substring(7)}@example.com`;
}

function generateRandomName() {
  const names = ['John', 'Jane', 'Carlos', 'Maria'];
  const lastNames = ['Doe', 'Smith', 'Johnson', 'Williams'];
  return `${names[Math.floor(Math.random() * names.length)]} ${lastNames[Math.floor(Math.random() * lastNames.length)]}`;
}

function generateRandomUrl() {
  const urls = ['https://github.com', 'https://google.com', 'https://dev.to'];
  return urls[Math.floor(Math.random() * urls.length)];
}

// --- API Actions ---

function createUser(name, email) {
  const payload = JSON.stringify({ name, email, password: 'SecurePass123!' });
  const params = { headers: { 'Content-Type': 'application/json' } };

  const response = http.post(USER_SERVICE, payload, params);
  if (response.status === 200 || response.status === 201) {
    const userData = JSON.parse(response.body);
    console.log(`✅ User created: ${name} (ID: ${userData.id})`);
    return userData;
  }
  return null;
}

function createShortUrl(baseUrl, userId) {
  const payload = JSON.stringify({ baseUrl, userId });
  const params = { headers: { 'Content-Type': 'application/json' } };

  const response = http.post(URL_SHORTENER, payload, params);
  if (response.status === 200 || response.status === 201) {
    const urlData = JSON.parse(response.body);
    console.log(`  📎 Short URL created: ${urlData.shortUrl} → ${baseUrl}`);
    return urlData;
  }
  return null;
}

function clickUrl(shortCodeValue) {
  const params = {
    headers: {
      'User-Agent': 'k6-load-test-agent',
      'Accept': 'application/json'
    },
  };

  const startTime = new Date();
  const response = http.get(`${URL_SHORTENER}/short-code/${shortCodeValue}`, params);
  clickDuration.add(new Date() - startTime);

  if (response.status === 200) {
    clicksCounter.add(1);
    return true;
  } else {
    console.log(`  ⚠️ Error ${response.status} on Code ${shortCodeValue}: ${response.body}`);
    return false;
  }
}

function getRanking() {
  console.log('\n🏆 FETCHING RANKING...');
  const response = http.get(`${STATISTICS_SERVICE}/ranking`);
  if (response.status === 200) {
    const ranking = JSON.parse(response.body);
    console.log('📊 FINAL RANKING:');
    ranking.forEach((item, index) => {
      console.log(`   Rank ${index + 1}: ${item.shortCode} - ${item.clickCount} clicks`);
    });
    return ranking;
  }
  return null;
}

export default function () {
  const users = [];
  const urlsByUser = {};

  group('PHASE 1: Users', () => {
    for (let i = 0; i < 3; i++) {
      const user = createUser(generateRandomName(), generateRandomEmail());
      if (user) { users.push(user); urlsByUser[user.id] = []; }
      sleep(0.5);
    }
  });

  group('PHASE 2: URLs', () => {
    users.forEach((user) => {
      for (let i = 0; i < 5; i++) { // Reducido a 5 para debug rápido
        const url = createShortUrl(generateRandomUrl(), user.id);
        if (url) urlsByUser[user.id].push(url);
        sleep(0.1);
      }
    });
  });

  group('PHASE 3: Clicks', () => {
    users.forEach((user) => {
      const userUrls = urlsByUser[user.id];
      const clickCount = 10;
      for (let i = 0; i < clickCount; i++) {
        const url = userUrls[Math.floor(Math.random() * userUrls.length)];
        clickUrl(url.shortUrl);
        if (i % 5 === 0) sleep(0.1);
      }
    });
  });

  group('PHASE 4: Ranking', () => {
    sleep(2);
    const ranking = getRanking();
    check(ranking, { 'Ranking exists': (r) => r !== null });
  });
}