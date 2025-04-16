import { useForm } from 'react-hook-form'
import axios from '../api/axiosInstance'
import { useNavigate } from 'react-router-dom'

export default function RegisterForm() {
  const { register, handleSubmit } = useForm()
  const navigate = useNavigate()

  const onSubmit = async (data) => {
    await axios.post('/auth/register', data)
    navigate('/login')
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <h2>Регистрация</h2>
      <input {...register('login')} placeholder="Логин" required />
      <input {...register('password')} type="password" placeholder="Пароль" required />
      <input {...register('firstName')} placeholder="Имя" required />
      <input {...register('lastName')} placeholder="Фамилия" required />
      <select {...register('role')}>
        <option value="TEACHER">Учитель</option>
        <option value="STUDENT">Ученик</option>
      </select>
      <button type="submit">Зарегистрироваться</button>
    </form>
  )
}
