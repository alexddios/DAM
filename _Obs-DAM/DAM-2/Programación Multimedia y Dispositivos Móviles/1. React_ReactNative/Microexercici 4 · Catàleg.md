## App.jsx
```jsx
import './App.css'  
import GameCard from "./components/GameCard.jsx"  
  
function App() {  
    const games = [  
        { id: 1, titol: "Celeste", plataforma:"PC",any: "2016" },  
        { id: 2, titol: "Hades",plataforma: "PS5",any: "2010" },  
        { id: 3,  titol: "Valorant", plataforma: "PC", any: "2020" },  
        {id: 4,  titol: "Pokémon Oleaje", plataforma: "Nintendo", any: "2027"},  
        {id: 5, titol:"GTA VI", plataforma: "Play Station 5", any: "2026"}  
    ];  
  return (  
    <>  
        {games.map(game => (  
            <GameCard key={game.id} titol={game.titol} plataforma={game.plataforma} any={game.any} />  
        ))}  
    </>  
  )  
}  
  
export default App
```
## GameCard.jsx
```jsx
import {useState} from "react";  
  
export default function GameCard({titol,plataforma,any}) {  
    const [fav,setFav] = useState(false);  
  
    return(  
        <>  
            <h1>{titol}</h1>  
            <h2>{plataforma}</h2>  
            <h3>{any}</h3>  
            <button onClick={() =>setFav(!fav)}>  
                {fav ? <span>❤️</span> : <span>💟</span> } Favorit  
            </button>  
        </>  
    )  
}
```