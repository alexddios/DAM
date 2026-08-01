const cuadriculaListaPokemon = document.getElementById('lista-pokemon');

function renderListaPokemon(listaPokemon) {
    cuadriculaListaPokemon.innerHTML = '';
    listaPokemon.forEach(pokemon => {
        const carta = document.createElement('div');
        carta.classList.add('carta-pokemon');

        const tiposHTML = pokemon.types.map(type =>
            `<span class="badge type-${type}">${type.toUpperCase()}</span>`
        ).join('');

        carta.innerHTML = `
            <a href="https://www.wikidex.net/wiki/${pokemon.name}" target="_blank" class="wikidex-link" title="Ver a ${pokemon.name} en WikiDex">
                <div class="contenedor-imagen-carta">
                    <img src="${pokemon.image}" alt="${pokemon.name}" class="pokemon-image">
                </div>
            </a>
            <div class="carta-info">
                <span class="pokemon-id">#${pokemon.id.toString().padStart(3, '0')}</span>
                <a href="https://www.wikidex.net/wiki/${pokemon.name}" target="_blank" class="wikidex-link">
                    <h3 class="pokemon-nombre">${pokemon.name}</h3>
                </a>
                <div class="pokemon-tipos">
                    ${tiposHTML}
                </div>
                <button class="add-btn" data-id="${pokemon.id}">Añadir a Equipo</button>
            </div>
        `;
        cuadriculaListaPokemon.appendChild(carta);
    });
}
renderListaPokemon(pokemonData);

//Lógica de equipo
let miEquipo = [];

const slotsEquipo = document.getElementById('slots-equipo');
const contadorEquipo = document.getElementById('contador-equipo');

cuadriculaListaPokemon.addEventListener('click',(event)=>{
    if(event.target.classList.contains('add-btn')){
        const pokemonId = parseInt(event.target.getAttribute('data-id'));
        aniadirPokemon(pokemonId);
    }
});
function aniadirPokemon(id) {
    if(miEquipo.length >= 6){
        alert("¡Tu equipo ya está lleno! Máximo 6 Pokémon.");
        return;
    }
    if (miEquipo.some(pokemon => pokemon.id === id)) { //Evitar duplicados
        alert("¡Este Pokémon ya está en tu equipo!");
        return;
    }

    const pokemon = pokemonData.find(pokemon => pokemon.id === id);

    miEquipo.push(pokemon);

    renderTeam();
}

function renderTeam() {
    contadorEquipo.textContent = `${miEquipo.length}/6`;

    slotsEquipo.innerHTML = '';

    if (miEquipo.length === 0) {
        slotsEquipo.innerHTML = '<p class="empty-message">Aún no has añadido ningún Pokémon.</p>';
        return;
    }
    miEquipo.forEach((pokemon, index) =>{
        const miembroEquipo = document.createElement('div');
        miembroEquipo.classList.add('miembro-equipo');
        miembroEquipo.innerHTML = `
            <img src="${pokemon.image}" alt="${pokemon.name}">
            <div class="member-info">
                <h4>${pokemon.name}</h4>
                <span>HP: ${pokemon.stats.hp} | ATK: ${pokemon.stats.attack}</span>
            </div>
            <button class="remove-btn" data-index="${index}">X</button>
        `;
        slotsEquipo.appendChild(miembroEquipo);
        }
    );
}
slotsEquipo.addEventListener('click', (event) => {
    if (event.target.classList.contains('remove-btn')) {
        const index = parseInt(event.target.getAttribute('data-index'));
        miEquipo.splice(index, 1);
        renderTeam();
    }
});

function eliminarPokemon(id) {
    miEquipo = miEquipo.filter(pokemon => pokemon.id !== id);
    renderTeam();
}
let textoActualSeleccionado = '';
let filtroTipoSeleccionado = 'all';

function aplicarFiltro() {
    let resultado = pokemonData;

    if(filtroTipoSeleccionado !== 'all') {
        resultado = resultado.filter(pokemon => pokemon.types.includes(filtroTipoSeleccionado));
    }

    if(textoActualSeleccionado.trim() !== '') {
        resultado = resultado.filter(pokemon => pokemon.name.toLowerCase().includes(textoActualSeleccionado));
    }

    renderListaPokemon(resultado);
}


const inputBusqueda = document.getElementById('buscador');

inputBusqueda.addEventListener('input', (event) => {
    textoActualSeleccionado = event.target.value.toLowerCase();
    aplicarFiltro();
});


const contenedorFiltros = document.getElementById('filtros');

contenedorFiltros.addEventListener('click', (event) => {
    if(event.target.classList.contains('filtro-btn')){

        filtroTipoSeleccionado = event.target.getAttribute('data-type');

        document.querySelectorAll('.filtro-btn').forEach(btn => btn.classList.remove('active'));
        event.target.classList.add('active');

        aplicarFiltro();
    }
});
