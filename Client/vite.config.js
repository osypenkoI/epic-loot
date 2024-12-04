import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
        '/api': 'https://epic-loot-backend-production.up.railway.app:8080',
    },
}
})

