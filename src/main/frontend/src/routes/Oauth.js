const CLIENT_ID = "6eeb8b5d78f4440ee5de867f2f4042c1";
const REDIRECT_URI = "http://localhost:8080/kakaoLogin";
const LOGOUT_REDIRECT_URI = "http://localhost:8080/";
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
export const KAKAO_LOGOUT_AUTH_URL = `https://kauth.kakao.com/oauth/logout?client_id=${CLIENT_ID}&logout_redirect_uri=${LOGOUT_REDIRECT_URI}`;
