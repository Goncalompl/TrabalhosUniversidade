using System.ComponentModel.DataAnnotations;

namespace Clientes.Models
{
    public class CondicoesPagamento
    {
        [Key]
        
        public int CondicoesId { get; set; }
        [Display(Name = "Condições de Pagamento")]
        public string Condicoes { get; set; }
    }
}
