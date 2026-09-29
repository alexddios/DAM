export default function ProfileCard() {
    const nombre = "Alex"
    const ciclo = "DAM"
    const curso = "Segundo"
    const dato = "Me gusta la fruta"
    return (
        <div>
            <h1> Mi nombre es {nombre}</h1>
            <h2> Estudio {ciclo}</h2>
            <h3> Exactamente en {curso}</h3>
            <h4> Un dato curioso es: {dato}</h4>
        </div>
    )
}