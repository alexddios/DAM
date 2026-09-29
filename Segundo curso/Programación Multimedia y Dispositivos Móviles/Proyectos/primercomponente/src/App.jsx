import './App.css'
import GameCard from "./components/GameCard.jsx"

function App() {
    const games = [
        { id: 1, titol: "Celeste" },
        { id: 2, titol: "Hades" },
        { id: 3, titol: "Portal 2" },
        {id: 4, titol: "Minecraft"}
    ];
  return (
    <>
        {games.map(game => (
            <GameCard key={game.id} titol={game.titol} />
        ))}
    </>
  )
}

export default App
