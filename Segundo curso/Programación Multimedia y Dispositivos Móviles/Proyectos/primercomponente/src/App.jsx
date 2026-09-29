import './App.css'
import GameCard from "./components/GameCard.jsx"
import Counter from "./components/Counter.jsx";


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

export default App
