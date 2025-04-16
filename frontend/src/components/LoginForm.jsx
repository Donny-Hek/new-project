import { useForm } from 'react-hook-form'
import { useDispatch } from 'react-redux'
import { login } from '../redux/authSlice'
import axios from '../api/axiosInstance'
import { useNavigate } from 'react-router-dom'

export default function LoginForm() {
  const { register, handleSubmit } = useForm()
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const onSubmit = async (data) => {
    const res = await axios.post('/auth/login', data)
    dispatch(login(res.data.token))
    navigate('/')
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <h2>Вход</h2>
      <input {...register('login')} placeholder="Логин" required />
      <input {...register('password')} type="password" placeholder="Пароль" required />
      <button type="submit">Войти</button>
    </form>
  )
}
