import {useState} from "react";
import {Alert, Pressable, StyleSheet, Text, TextInput, View} from "react-native";
import {router} from "expo-router";
import {useLugares} from "../context/lugares";
import {colores} from "../constants/colores";

// Opciones que puede elegir el usuario. Las tres primeras son las de llocs.js.
const CATEGORIAS = ['Naturaleza', 'Arquitectura', 'Mar', 'Otros'];

export default function Formulario(){
    const {añadirLugar} = useLugares();
    const [nombre,setNombre] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [categoria, setCategoria] = useState(CATEGORIAS[0]);

    function guardar(){
        const limpio = nombre.trim();
        if (limpio.length === 0 ){
            Alert.alert('Nombre vacio', 'Introduce el nombre');
            return;
        }
        añadirLugar({ nombre: limpio, descripcion: descripcion.trim(), categoria });
        Alert.alert('Hecho', 'Lugar añadido')
        if (router.canGoBack()) {
            router.back();
        } else {
            router.replace('/');
        }
    }

    return(
        <View style={styles.contenedor}>
            <Text style={styles.etiqueta}>NOMBRE</Text>
            <TextInput
                value={nombre}
                onChangeText={setNombre}
                placeholder='Nombre del sitio'
                placeholderTextColor={colores.textoSuave}
                style={styles.campo}
            />
            <Text style={styles.etiqueta}>DESCRIPCIÓN</Text>
            <TextInput
                multiline
                value={descripcion}
                onChangeText={setDescripcion}
                placeholder='Descripción del lugar'
                placeholderTextColor={colores.textoSuave}
                style={[styles.campo, styles.campoLargo]}
            />
            <Text style={styles.etiqueta}>CATEGORÍA</Text>
            {/* Un botón por categoría; el seleccionado se resalta en azul. */}
            <View style={styles.opciones}>
                {CATEGORIAS.map((opcion) => {
                    const elegida = opcion === categoria;
                    return (
                        <Pressable
                            key={opcion}
                            accessibilityRole="radio"
                            accessibilityState={{ checked: elegida }}
                            onPress={() => setCategoria(opcion)}
                            style={({ pressed }) => [
                                styles.opcion,
                                elegida && styles.opcionElegida,
                                pressed && styles.pulsado,
                            ]}
                        >
                            <Text style={[styles.textoOpcion, elegida && styles.textoOpcionElegida]}>
                                {opcion}
                            </Text>
                        </Pressable>
                    );
                })}
            </View>
            <Pressable
                accessibilityRole="button"
                accessibilityLabel={`Guardar`}
                onPress={guardar}
                style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
            >
                <Text style={styles.textoBoton}>Guardar</Text>
            </Pressable>
        </View>
    )
}

const styles = StyleSheet.create({
    // Separa el formulario de los bordes y limita el ancho en pantallas grandes.
    contenedor: {
        padding: 20,
        gap: 8,
        width: '100%',
        maxWidth: 640,
        alignSelf: 'center',
    },
    etiqueta: {
        fontSize: 14,
        fontWeight: '700',
        color: colores.texto,
        marginTop: 10,
    },
    // Campo con la línea inferior más gruesa en el color principal.
    campo: {
        backgroundColor: colores.superficie,
        borderWidth: 1,
        borderColor: colores.borde,
        borderBottomWidth: 2,
        borderBottomColor: colores.principal,
        borderRadius: 8,
        paddingHorizontal: 14,
        paddingVertical: 12,
        minHeight: 48,
        fontSize: 16,
        color: colores.texto,
    },
    // Solo para la descripción: más alto y con el texto empezando arriba (Android).
    campoLargo: {
        minHeight: 120,
        textAlignVertical: 'top',
    },
    opciones: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, marginBottom: 8 },
    opcion: {
        backgroundColor: colores.superficie,
        borderWidth: 1,
        borderColor: colores.borde,
        borderRadius: 8,
        paddingHorizontal: 16,
        minHeight: 40,
        justifyContent: 'center',
    },
    opcionElegida: { backgroundColor: colores.principalSuave, borderColor: colores.principal },
    textoOpcion: { fontSize: 15, color: colores.textoSuave },
    textoOpcionElegida: { color: colores.principal, fontWeight: '700' },
    boton: {
        backgroundColor: colores.acento,
        borderRadius: 999,
        minHeight: 50,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 12,
    },
    pulsado: { opacity: 0.7 },
    textoBoton: { color: colores.sobrePrincipal, fontSize: 17, fontWeight: '700' }
});
