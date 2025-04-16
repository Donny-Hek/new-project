import { useState } from 'react'
import axios from '../api/axiosInstance'

export default function Dashboard() {
  const [image, setImage] = useState(null)
  const [refFile, setRefFile] = useState(null)

  const uploadImage = async () => {
    const formData = new FormData()
    formData.append('file', image)
    await axios.post('/images/upload', formData)
    alert('Изображение загружено')
  }

  const uploadReference = async () => {
    const formData = new FormData()
    formData.append('file', refFile)
    await axios.post('/images/import-reference', formData)
    alert('Эталон загружен')
  }

  const downloadReport = async () => {
    const res = await axios.get('/images/export', { responseType: 'blob' })
    const url = window.URL.createObjectURL(new Blob([res.data]))
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', 'results.xlsx')
    document.body.appendChild(link)
    link.click()
  }

  return (
    <div>
      <h2>Панель управления</h2>

      <h3>Загрузить бланк</h3>
      <input type="file" onChange={(e) => setImage(e.target.files[0])} />
      <button onClick={uploadImage}>Загрузить</button>

      <h3>Загрузить эталон</h3>
      <input type="file" onChange={(e) => setRefFile(e.target.files[0])} />
      <button onClick={uploadReference}>Импортировать</button>

      <h3>Скачать отчёт</h3>
      <button onClick={downloadReport}>Скачать Excel</button>
    </div>
  )
}
