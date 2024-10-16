/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {},
    fontFamily: {
      logo: ["Yeseva One"],
    },
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: ["synthwave"],
  },
};
