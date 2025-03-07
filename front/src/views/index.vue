<template>
  <div class="app-container home">
    <div class="welcome-section">
      <h1>欢迎使用“二钱银子”旅游行程规划助手</h1>
      <p>让您的旅程规划更简单、更愉快。</p>
    </div>
    <div class="donation-qr">
      <img src="" alt="打赏二维码">
      <p>如果您觉得有帮助，请随意打赏！</p>
    </div>
  </div>
</template>

<script setup name="Index">
const version = ref('1.0.0')

function goTarget(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">

.app-container.home {
  display: flex;
  justify-content: space-between;
  padding: 2em;
  font-family: 'Noto Sans JP', sans-serif;
  background-color: #f8f8f8;
}

.welcome-section {
  max-width: 60%;
  color: #333;
}

.welcome-section h1 {
  font-size: 2.5em;
  margin-bottom: 0.5em;
}

.welcome-section p {
  font-size: 1.2em;
  color: #666;
}

.donation-qr {
  text-align: center;
  padding: 1em;
  background-color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, .1);
  border-radius: 8px;
  width: 20%;
}

.donation-qr img {
  width: 100%;
  border-radius: 8px;
}

.donation-qr p {
  margin-top: 0.5em;
  font-size: 1em;
  color: #999;
}

.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

