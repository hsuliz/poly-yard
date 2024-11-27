import axios from "axios"
import { getToken } from "@josempgon/vue-keycloak"

const baseURL = import.meta.env.VITE_API_URL

const apiClient = axios.create({
  baseURL
})

apiClient.interceptors.request.use(
  async (config) => {
    const token = await getToken()
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("API Error:", error)
    return Promise.reject(error)
  }
)

export { apiClient }
