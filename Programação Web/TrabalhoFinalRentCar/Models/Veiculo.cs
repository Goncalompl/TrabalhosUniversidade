using System.ComponentModel.DataAnnotations.Schema;

namespace TrabalhoFinalRentCar.Models
{
    public class Veiculo
    {
        public int Id { get; set; }
        public string Marca { get; set; }
        public string Matricula { get; set; }
        public string Tipo { get; set; }
        public string Localizacao { get; set; }
        public DateTime DataLevantamento { get; set; }
        public DateTime DataEntrega { get; set; }
        public double Custo { get; set; }
        public int Ativo { get; set; }
    
        public int? EmpresaAssociadaId { get; set; }
        public Empresa EmpresaAssociada { get; set; }
        public double NumeroKM { get; set; }

        public bool Danos;
        public String Observacoes { get; set; }
        [NotMapped]

        public IFormFile FotoVeiculo { get; set; }
    }
}
