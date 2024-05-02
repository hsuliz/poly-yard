import { Route, Routes } from "react-router-dom"
import BookDetails from "./book/BookDetails"
import React from "react"
import App from "./App"

const Router = () => (
  <Routes>
    <Route path="/" element={<App />} />
    <Route path="/book/:bookId" element={<BookDetails />} />
  </Routes>
)

export default Router
