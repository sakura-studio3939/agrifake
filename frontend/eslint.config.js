// ========================================
// ESLint 設定
// 本プロジェクトの JavaScript は
// ブラウザ上での実行を前提としているため、
// browser 環境のグローバル変数を有効化する
// ========================================
export default [
  {
    files: ["**/*.js"],
    languageOptions: {
      ecmaVersion: 2021,
      sourceType: "module",
    },
    env: {
      browser: true
    },
    rules: {
      "no-undef": "error",
      "no-unused-vars": "warn"
    }
  }
];
