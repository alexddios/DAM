## ProfileCard.jsx
```js
export default function ProfileCard() {  
    const nombre = "Alex"  
    const ciclo = "Grado Superior"  
    const curso = "Segundo de DAM"  
    const dato = "Me gusta la fruta"  
    return (  
        <div>            <p> Mi nombre es {nombre}</p>  
            <p> Estudio {ciclo}</p>  
            <p> Exactamente en {curso}</p>  
            <p> Un dato curioso es: {dato}</p>  
        </div>  
    )}
```
## App.jsx
```js
import './App.css'

import ProfileCard from "./ProfileCard.jsx";


function App() {
  return (
    <>
      <div>
          <ProfileCard/>
          <ProfileCard/>
          <ProfileCard/>
      </div>
    </>
  )
}

export default App
```