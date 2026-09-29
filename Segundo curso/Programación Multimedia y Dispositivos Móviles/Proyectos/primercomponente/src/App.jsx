import './App.css'
import GameCard from "./components/GameCard.jsx"
import {useState} from "react";


function App() {
  return (
    <>
        <GameCard titol ="Valorant" plataforma = "PC" any = "2020"/>
        <GameCard titol ="Pokémon Oleaje" plataforma = "Nintendo" any = "2027"/>
        <GameCard titol ="GTA VI" plataforma = "Play Station 5" any = "2026"/>
        <Counter/>
    </>
  )
}
function Counter(){
    const [count,setCount] = useState(0);
    return(
        <button onClick={()=>setCount(count+1)}>
            Clics : {count}
        </button>
    );
}

export default App
