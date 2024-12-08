namespace TrabalhoFinalRentCar.Models
{
    public class Empresa
    {
        public int Id { get; set; }
        public string Nome { get; set; }
        public int? ReviewId { get; set; }
        public Review Review { get; set; }
    }
}
