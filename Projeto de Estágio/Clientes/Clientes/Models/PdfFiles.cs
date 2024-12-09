using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Clientes.Models
{
    public class PdfFiles
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int FileId { get; set; } = 0;
        public string Name { get; set; } = "";
        public string Path { get; set; } = "";
        [Display(Name = "Cliente")]
        public int ClientePdfID { get; set; }
        public Cliente ClientePdf { get; set; }

       
    }
}
