```jsx title:"Formulario React"
import { useState } from 'react';  
  
function App() {  
  const [nombre, setNombre] = useState('');  
  const [correo, setCorreo] = useState('');  
  const [edad, setEdad] = useState(0);  
  
  function enviar(e) {  
    e.preventDefault(); // evita que el formulario recargue la página  
    console.log({ nombre, correo, edad });  
  }  
  
  return (  
    <>  
      <form onSubmit={enviar}>  
        <input  
          type="text"  
          placeholder="Nombre"  
          value={nombre}  
          onChange={(e) => setNombre(e.target.value)}  
        />  
        <input          type="email"  
          placeholder="Correo"  
          value={correo}  
          onChange={(e) => setCorreo(e.target.value)}  
        />  
        <input          type="number"  
          placeholder="Edad"  
          min={0}  
          max={120}  
          value={edad}  
          onChange={(e) => setEdad(Number(e.target.value))}  
        />  
        <button type="submit">Enviar</button>  
      </form>  
    </>  
  );  
}  
  
export default App;
```