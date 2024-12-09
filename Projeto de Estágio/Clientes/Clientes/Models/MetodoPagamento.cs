using System.ComponentModel.DataAnnotations;

namespace Clientes.Models
{
    public class MetodoPagamento
    {
        [Key]
        
        public int MetodoId { get; set; }
        [Display(Name = "Metodo de Pagamento")]
        public string Metodo { get; set; }
    }
}
