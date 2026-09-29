## GameCard.jsx
```js
export default function GameCard({titol,plataforma,any}) {  
    return(  
        <>  
            <h1>{titol}</h1>  
            <h2>{plataforma}</h2>  
            <h3>{any}</h3>  
        </>  
    )  
}
```
## App.jsx
```js
import './App.css'  
import GameCard from "./components/GameCard.jsx"  
  
  
function App() {  
  return (  
    <>  
        <GameCard titol ="Valorant" plataforma = "PC" any = "2020"/>  
        <GameCard titol ="Pokémon Oleaje" plataforma = "Nintendo" any = "2027"/>  
        <GameCard titol ="GTA VI" plataforma = "Play Station 5" any = "2026"/>  
    </>  
  )  
}  
  
export default App
```