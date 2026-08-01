declare option output:indent "yes";

<incidentsExterns>
{
  for $x in doc("C:\Users\alexd\OneDrive\Alex\DAM2526\Lenguaje de Marcas\Ejercicios\PracticaXQuery\incidents.xml")
  /incidents/incident
  where $x/tecnic ="Laia"
  return
    <incident risc="{data($x/@risc)}" estat="{data($x/@estat)}">
      {$x/titol}
    </incident>
}
</incidentsExterns>