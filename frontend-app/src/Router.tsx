import { Route, Routes } from "react-router-dom"
import React from "react"
import App from "./App"

const Router = () => (
  <Routes>
    <Route path="/" element={<App />} />
  </Routes>
)

export default Router
