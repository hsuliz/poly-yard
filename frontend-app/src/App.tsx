import React from "react"
import Layout from "./Layout"
import Home from "./home/Home"
import { Route, Routes } from "react-router-dom"
import BookCard from "./book/BookCard"

const App = () => (
  <div className="bg-gray-900 h-screen w-screen">
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="/book/:bookId" element={<BookCard />} />
      </Route>
    </Routes>
  </div>
)

export default App
