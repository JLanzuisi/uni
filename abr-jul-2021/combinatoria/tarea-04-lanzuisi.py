def math_pensum_digraph():
    """
    Docs
    """

    import networkx as nx
    import matplotlib.pyplot as plt

    edges = [
        ("Mate I", "Mate II"),
        ("Mate I", "Física I"),
        ("Inglés I", "Inglés II"),
        ("Lenguaje I", "Lenguaje II"),
        ("Sociales I", "Sociales II"),
        ("Mate II", "Mate III"),
        ("Mate II", "Física II"),
        ("Física I", "Física II"),
        ("Inglés II", "Inglés III"),
        ("Lenguaje II", "Lenguaje III"),
        ("Sociales II", "Sociales III"),
        ("Mate III", "Álgebra I"),
        ("Mate III", "Cálculo I"),
        ("Mate III", "Geometría I"),
        ("Inglés III", "Inglés\npara\nmatemáticos I"),
        ("Álgebra I", "Álgebra II"),
        ("Cálculo I", "Cálculo II"),
        ("Geometría I", "Geometría II"),
        ("Inglés\npara\nmatemáticos I", "Inglés\npara\nmatemáticos II"),
        ("Álgebra II", "Álgebra III"),
        ("Cálculo II", "Cálculo III"),
        ("Geometría II", "Geometría III"),
        ("Inglés\npara\nmatemáticos II", "Inglés\npara\nmatemáticos III"),
	("Álgebra III", "Intro.\nTeo.\nGrupos"),
	("Álgebra III", "Mat.\nDiscretas"),
	("Cálculo III", "Análisis I"),
        ("Intro.\nTeo.\nGrupos", "Intro.\nTeo.\nAnillos"),
	("Análisis I", "Análisis II"),
	("Análisis I", "Topología I"),
	("Mat.\nDiscretas", "Combinatoria"),
	("Mat.\nDiscretas", "Intro.\nProbabilidades"),
	("Mat.\nDiscretas", "Algoritmos I"),
	("Análisis II", "Análisis III"),
	("Topología I", "Ec.\nDiferenciales I"),
	("Intro.\nProbabilidades", "Intro.\nProcesos\nEstocásticos"),
	("Análisis III", "Análisis IV"),
	("Análisis III", "Ec.\nDiferenciales I"),
	("Intro.\nProcesos\nEstocásticos", "Elect.\nProbabilidad\nEstadística"),
	("Análisis IV", "Análisis V"),
	("Ec.\nDiferenciales I", "Topología II"),
	("Algoritmos I", "Algoritmos II"),
	("Análisis V", "Análisis VI"),
	("Combinatoria", "Elect.\nMat. Discretas"),
	("Análisis VI", "Ec.\nDiferenciales II"),
	("Topología II", "Ec.\nDiferenciales II"),
    ]

    nodes = [
        "Geometría",
        "Estudio\nGeneral 1",
        "Estudio\nGeneral 2",
        "Estudio\nGeneral 3",
        "Estudio\nGeneral 4",
        "Estudio\nGeneral 5",
        "Estudio\nGeneral 6",
        "Electiva\nMatemática 1",
        "Electiva\nMatemática 2",
	"Electiva\nAfín",
        "Seminario 1",
        "Seminario 2"
    ]

    G = nx.DiGraph()

    G.add_edges_from(edges, weight=1)
    G.add_nodes_from(nodes)

    edgelabels = nx.get_edge_attributes(G,'weight')

    pos = nx.drawing.nx_agraph.graphviz_layout(G)

    plt.figure(figsize=(22,22))
    nx.draw(G, pos=pos, with_labels=True, node_color='white', verticalalignment='center', node_size=5000)
    nx.draw_networkx_edge_labels(G, pos, edgelabels)
    
    plt.savefig('graph.pdf')

math_pensum_digraph()




