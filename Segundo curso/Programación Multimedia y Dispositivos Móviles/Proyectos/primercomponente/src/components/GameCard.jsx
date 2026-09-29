import {useState} from "react";

export default function GameCard({titol}) {
    const [fav,setFav] = useState(false);

    return(
        <>
            <h1>{titol}</h1>
            <button onClick={() =>setFav(!fav)}>
                {fav ? <span>❤️</span> : <span>💟</span> } Favorit
            </button>
        </>
    )
}