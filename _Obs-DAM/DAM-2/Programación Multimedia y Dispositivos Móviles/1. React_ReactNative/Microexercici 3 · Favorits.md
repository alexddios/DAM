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