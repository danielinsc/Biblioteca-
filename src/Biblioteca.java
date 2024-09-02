import java.util.ArrayList;


abstract class ItemBiblioteca {
    String titulo;
    String autor;
    boolean disponivel;


    public ItemBiblioteca(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }

    abstract void emprestar() throws ItemNaoDisponivelException;
    abstract void devolver();
}


class Livro extends ItemBiblioteca {

    public Livro(String titulo, String autor) {
        super(titulo, autor);
    }


    @Override
    void emprestar() throws ItemNaoDisponivelException {
        if (disponivel) {
            disponivel = false;
            System.out.println("Livro '" + titulo + "' emprestado com sucesso.");
        } else {
            throw new ItemNaoDisponivelException("O livro '" + titulo + "' não está disponível.");
        }
    }


    @Override
    void devolver() {
        disponivel = true;
        System.out.println("Livro '" + titulo + "' devolvido com sucesso.");
    }
}


class Periodico extends ItemBiblioteca {

    public Periodico(String titulo, String autor) {
        super(titulo, autor);
    }


    @Override
    void emprestar() throws ItemNaoDisponivelException {
        if (disponivel) {
            disponivel = false;
            System.out.println("Periódico '" + titulo + "' emprestado com sucesso.");
        } else {
            throw new ItemNaoDisponivelException("O periódico '" + titulo + "' não está disponível.");
        }
    }


    @Override
    void devolver() {
        disponivel = true;
        System.out.println("Periódico '" + titulo + "' devolvido com sucesso.");
    }
}


class MidiaDigital extends ItemBiblioteca {
    // Construtor
    public MidiaDigital(String titulo, String autor) {
        super(titulo, autor);
    }

    // Implementação do método emprestar
    @Override
    void emprestar() throws ItemNaoDisponivelException {
        if (disponivel) {
            disponivel = false;
            System.out.println("Mídia digital '" + titulo + "' emprestada com sucesso.");
        } else {
            throw new ItemNaoDisponivelException("A mídia digital '" + titulo + "' não está disponível.");
        }
    }

    // Implementação do método devolver
    @Override
    void devolver() {
        disponivel = true;
        System.out.println("Mídia digital '" + titulo + "' devolvida com sucesso.");
    }
}

// Interface para os livros na biblioteca
interface Gerenciavel {
    void adicionarItem(ItemBiblioteca item);
    void removerItem(ItemBiblioteca item) throws ItemNaoEncontradoException;
    ItemBiblioteca buscarItem(String titulo) throws ItemNaoEncontradoException;
}

// Gerenciamento dos livros
class Biblioteca implements Gerenciavel {
    private ArrayList<ItemBiblioteca> itens;


    public Biblioteca() {
        itens = new ArrayList<>();
    }


    @Override
    public void adicionarItem(ItemBiblioteca item) {
        itens.add(item);
        System.out.println("Item '" + item.titulo + "' adicionado à biblioteca.");
    }


    @Override
    public void removerItem(ItemBiblioteca item) throws ItemNaoEncontradoException {
        if (itens.contains(item)) {
            itens.remove(item);
            System.out.println("Item '" + item.titulo + "' removido da biblioteca.");
        } else {
            throw new ItemNaoEncontradoException("O item '" + item.titulo + "' não foi encontrado na biblioteca.");
        }
    }

    @Override
    public ItemBiblioteca buscarItem(String titulo) throws ItemNaoEncontradoException {
        for (ItemBiblioteca item : itens) {
            if (item.titulo.equalsIgnoreCase(titulo)) {
                return item;
            }
        }
        throw new ItemNaoEncontradoException("O item com o título '" + titulo + "' não foi encontrado na biblioteca.");
    }


    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Criando livros
        Livro livro = new Livro("O Hobbit", "J.R.R. Tolkien");
        Periodico periodico = new Periodico("A Batalha do Apocalipse", "Eduardo Sphor");
        MidiaDigital midiaDigital = new MidiaDigital("O Alquimista", "Paulo Coelho");


        biblioteca.adicionarItem(livro);
        biblioteca.adicionarItem(periodico);
        biblioteca.adicionarItem(midiaDigital);

        try {
            // Emprestando e devolvendo
            ItemBiblioteca item = biblioteca.buscarItem("O Hobbit");
            item.emprestar();  // Empresta o livro
            item.devolver();   // Devolve o livro

            // Tentando emprestar um livro indisponível
            item.emprestar();
            item.emprestar();  // Tentando emprestar o livro já emprestado (lança exceção)
        } catch (ItemNaoDisponivelException | ItemNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        try {

            ItemBiblioteca item = biblioteca.buscarItem("Revista Científica");
            biblioteca.removerItem(item);


            biblioteca.buscarItem("Revista Científica");
        } catch (ItemNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}


class ItemNaoDisponivelException extends Exception {
    public ItemNaoDisponivelException(String message) {
        super(message);
    }
}


class ItemNaoEncontradoException extends Exception {
    public ItemNaoEncontradoException(String message) {
        super(message);
    }
}

