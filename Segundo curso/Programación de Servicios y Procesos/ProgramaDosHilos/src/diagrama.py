import os
import shutil

from graphviz import Digraph

GRAPHVIZ_BIN = r"C:\Program Files\Graphviz\bin"

if not shutil.which("dot") and os.path.exists(os.path.join(GRAPHVIZ_BIN, "dot.exe")):
    os.environ["PATH"] = GRAPHVIZ_BIN + os.pathsep + os.environ.get("PATH", "")

if not shutil.which("dot"):
    raise RuntimeError(
        "No se encontró Graphviz. Instala Graphviz y añade su carpeta bin al PATH."
    )

dot = Digraph(comment='Diagrama de Clases MVP')
dot.attr(rankdir='TB', size='10,10')
dot.attr('node', shape='record', fontname='Helvetica', fontsize='10')
dot.attr('edge', fontname='Helvetica', fontsize='10')

# INTERFACES
dot.node('IView', r'{\<\<interface\>\>\nIView|+ setPresenter(p: IPresenter)\n+ updateCount(count: int)}')
dot.node('IModel', r'{\<\<interface\>\>\nIModel|+ startSimulation()\n+ setPresenter(p: IPresenter)}')
dot.node('IPresenter', r'{\<\<interface\>\>\nIPresenter|+ onSimulationStarted()\n+ updateCounter(value: int)}')

# CLASES IMPLEMENTADORAS
dot.node('MyView', r'{MyView|- presenter: IPresenter\n- resultLabel: JLabel\n- startButton: JButton|+ MyView()\n+ setPresenter(p: IPresenter)\n+ updateCount(count: int)}')
dot.node('MyModel', r'{MyModel|- presenter: IPresenter\n- resource: Resource|+ MyModel()\n+ startSimulation()\n+ setPresenter(p: IPresenter)}')
dot.node('MyPresenter', r'{MyPresenter|- view: IView\n- model: IModel|+ MyPresenter(v: IView, m: IModel)\n+ onSimulationStarted()\n+ updateCounter(value: int)}')

# COMPONENTES DEL MODELO
dot.node('Resource', r'{Resource|- count: int = 0|+ getCount(): int\n+ add()\n+ remove()}')
dot.node('Producer', r'{Producer|- resource: Resource\n- model: IModel|+ Producer(r: Resource, m: IModel)\n+ run()}')
dot.node('Consumer', r'{Consumer|- resource: Resource\n- model: IModel|+ Consumer(r: Resource, m: IModel)\n+ run()}')
dot.node('Runnable', r'{\<\<interface\>\>\nRunnable|+ run()}')

# RELACIONES - REALIZACIONES (Implementaciones)
dot.edge('MyView', 'IView', style='dashed', arrowhead='empty')
dot.edge('MyModel', 'IModel', style='dashed', arrowhead='empty')
dot.edge('MyPresenter', 'IPresenter', style='dashed', arrowhead='empty')
dot.edge('Producer', 'Runnable', style='dashed', arrowhead='empty')
dot.edge('Consumer', 'Runnable', style='dashed', arrowhead='empty')

# RELACIONES - DEPENDENCIAS / ASOCIACIONES
dot.edge('MyPresenter', 'IView', label='- view', arrowhead='vee')
dot.edge('MyPresenter', 'IModel', label='- model', arrowhead='vee')
dot.edge('MyView', 'IPresenter', label='- presenter', arrowhead='vee')
dot.edge('MyModel', 'IPresenter', label='- presenter', arrowhead='vee')

# RELACIONES INTERNAS DEL MODELO (Composición/Agregación)
dot.edge('Resource', 'MyModel', arrowhead='diamond', dir='forward')
dot.edge('MyModel', 'Producer', label='instancia', style='dashed', arrowhead='vee')
dot.edge('MyModel', 'Consumer', label='instancia', style='dashed', arrowhead='vee')
dot.edge('Producer', 'Resource', label='- resource', arrowhead='vee')
dot.edge('Consumer', 'Resource', label='- resource', arrowhead='vee')

# RENDERIZADO (Generará 'mvp_class_diagram.svg' en tu carpeta actual)
dot.render('mvp_class_diagram', format='svg')
print("Diagrama generado con éxito")