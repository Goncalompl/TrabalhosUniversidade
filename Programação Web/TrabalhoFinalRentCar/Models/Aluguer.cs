namespace TrabalhoFinalRentCar.Models
{
    public class Aluguer
    {
        public int Id { get; set; }
        public DateTime DataInicio { get; set; }
        public DateTime DataFim { get; set; }
        public Decimal Preco { get; set; }
        public string Detalhes { get; set; }
        public int ClienteId { get; set; }
        public String Cliente { get; set; }
    }
}
