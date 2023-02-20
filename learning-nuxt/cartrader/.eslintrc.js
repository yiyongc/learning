module.exports = {
  root: true,
  extends: ["@nuxtjs/eslint-config", "plugin:prettier/recommended"],
  rules: {
    // override/add rules settings here, such as:
    // 'vue/no-unused-vars': 'error'
    "vue/multi-word-component-names": "off",
  },
};
