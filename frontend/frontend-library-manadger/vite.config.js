import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

//https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  define: {
    global: {}, // Добавлено для устранения ошибки "global is not defined"
  },
});

// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'
//
// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [react()],
// })



