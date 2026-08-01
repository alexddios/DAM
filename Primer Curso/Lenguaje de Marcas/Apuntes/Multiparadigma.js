// Procedimental
const nums = [1,2,3,4,5];
let dobles=[]

for(let i=0; i<nums.length; i++){
    dobles.push(nums[i]*2)
}
console.log(dobles)


// Funcional
const nums2 = [1,2,3,4,5];
const dobles2 = nums2.map(num => num*2);
console.log(dobles2)

const petits = nums.filter(num => num<3);
console.log(petits)

//Orientat a objectes
const alumne = {
    nom: "Alex",
    edat: 21,
    curs: "DAM",
    nota:10
}

console.log(alumne)

const alumnes = [
    {nom:"Alex", edat:21, curs:"DAM", nota:10},
    {nom:"Luis", edat:22, curs:"DAW", nota:9},
    {nom:"Marta", edat:20, curs:"DAM", nota:8},
]
for(let alumne of alumnes){
    console.log(alumne.nom)
}

