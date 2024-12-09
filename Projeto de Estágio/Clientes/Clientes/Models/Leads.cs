using System.ComponentModel.DataAnnotations;
using System.Xml.Linq;

namespace Clientes.Models
{
    public class Leads
    {
        [Key]
        public int Id { get; set; }
        public string Nome { get; set; }
        [Display(Name = "Responsável")]
        public string Responsavel { get; set; }
        [Display(Name = "Origem do Contacto")]
        public string? PrimeiroContacto { get; set; }
        [Display(Name = "Data do Primeiro Contacto")]
        [DataType(DataType.Date)]
        public DateTime? DataPrimeiroC { get; set; }
        [Display(Name = "A Fazer")]
        public string? AFazer { get; set; }
        [Display(Name = "Reunião")]
        public string? Reuniao { get; set; }
        [Display(Name = "Alvo da proposta")]
        public string? Proposta { get; set; }
        public string? Resultados { get; set; }
        [Display(Name = "Observações")]
        public string? Observacoes { get; set; }
        public string? Detalhes { get; set; }
        public string Email { get; set; }
        public int Telefone { get; set; }
        [Display(Name = "Setor de Atividade")]
        public string SetorAtividade { get; set; }
        [Display(Name = "Estado")]
        public int QuenteId { get; set; }
        [Display(Name = "Estado")]
        public Estado Quente { get; set; }

    }
}
