import { View, Text,FlatList } from 'react-native';
import {useState, useEffect} from "react";

export default function descubrir() {
    const [resultados, setResultados] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState(null);
    useEffect(() => {
        async function obtenerDatos() {
            try {
                // Cambiamos la URL por una 100% estable para probar
                const respuesta = await fetch('https://jsonplaceholder.typicode.com/users');
                const datos = await respuesta.json();
                setResultados(datos);
            } catch (e) {
                console.error("Motivo del fallo:", e); // Esto saldrá en tu terminal
                setError("Error al cargar los datos");
            } finally {
                setCargando(false);
            }
        }
        obtenerDatos();
    }, []);
    if (cargando) {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <Text>Buscando lugares...</Text>
            </View>
        );
    }
    if (error) {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <Text style={{ color: 'red' }}>{error}</Text>
            </View>
        );
    }
    return (
        <View style={{ flex: 1, padding: 16 }}>
            <FlatList
                data={resultados}
                keyExtractor={(item) => item.name}
                renderItem={({ item }) => (
                    <View style={{ padding: 16, borderBottomWidth: 1, borderColor: '#ccc' }}>
                        <Text style={{ fontSize: 16, fontWeight: 'bold' }}>{item.name}</Text>
                        <Text style={{ color: 'gray' }}>{item.web_pages[0]}</Text>
                    </View>
                )}
            />
        </View>
    );
}