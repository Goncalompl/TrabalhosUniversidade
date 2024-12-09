using System.ComponentModel.DataAnnotations;

namespace Clientes.Models
{
    public class Estado
    {
        [Key]
        public int EstadoId { get; set; }
        public string EstadoNome { get; set; }
        public string EstadoLeadNome { get; set; }
    }
}
