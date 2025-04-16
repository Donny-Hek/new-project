import { Routes, Route, Navigate } from 'react-router-dom'
import LoginForm from './components/LoginForm.jsx'
import RegisterForm from './components/RegisterForm.jsx'
import Dashboard from './components/Dashboard.jsx'
import { useSelector } from 'react-redux'

function App() {
  const isAuth = useSelector((state) => state.auth.isAuthenticated)

  return (
    <Routes>
      <Route path="/" element={isAuth ? <Dashboard /> : <Navigate to="/login" />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<RegisterForm />} />
    </Routes>
  )
}

export default App
