package br.com.devmedia.cleancode.modelo.pedido;

/**
 *
 */
public enum StatusPedido {

    ABERTO() {
        @Override
        public void faturar(Pedido pedido) {
            pedido.calcularValorTotalItens();
            pedido.aplicarDesconto();
            pedido.calcularValorFinal();
            pedido.setEstado(FATURADO);
        }

        @Override
        public void cancelar(Pedido pedido) {
            throw new IllegalStateException("Pedido não está faturado");
        }

        @Override
        public boolean podeIncluirNovoItem() {
            return true;
        }

        @Override
        public boolean podeRemoverItem() {
            return true;
        }
    },

    CANCELADO() {
        @Override
        public void faturar(Pedido pedido) {
            throw new IllegalStateException("Pedido não está aberto");
        }

        @Override
        public void cancelar(Pedido pedido) {
            throw new IllegalStateException("Pedido já cancelado");
        }

        @Override
        public boolean podeIncluirNovoItem() {
            return false;
        }

        @Override
        public boolean podeRemoverItem() {
            return false;
        }
    },
    FATURADO() {
        @Override
        public void faturar(Pedido pedido) {
            throw new IllegalStateException("Pedido já faturado");
        }

        @Override
        public void cancelar(Pedido pedido) {
            pedido.setEstado(CANCELADO);
        }

        @Override
        public boolean podeIncluirNovoItem() {
            return false;
        }

        @Override
        public boolean podeRemoverItem() {
            return false;
        }
    };

    public abstract void faturar(Pedido pedido);

    public abstract void cancelar(Pedido pedido);

    public abstract boolean podeIncluirNovoItem();

    public abstract boolean podeRemoverItem();
}
