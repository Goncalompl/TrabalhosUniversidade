using System.ComponentModel.DataAnnotations;

namespace Clientes.Models
{
    public class EnvioFatura
    {
        [Key]
        
        public int FaturaId { get; set; }
        [Display(Name = "Envio de Fatura")]
        public string Fatura { get; set; }
    }
}
