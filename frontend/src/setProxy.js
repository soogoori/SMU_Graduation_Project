const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(

    createProxyMiddleware('/api',{
      target: 'http://localhost:8080',	// 서버 URL or localhost:설정한 포트번호
      changeOrigin: true,
    })
  );
};