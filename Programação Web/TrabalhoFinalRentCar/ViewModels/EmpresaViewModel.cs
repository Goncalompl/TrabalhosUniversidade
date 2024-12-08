using System.ComponentModel.DataAnnotations;
using System.Xml.Linq;

namespace TrabalhoFinalRentCar.ViewModels
{
    public class EmpresaViewModel
    {
        [Display(Name = "Nome Empresa")]
        public string Empresa { get; set; }
        public int EmpresaId { get; set; }
    }
}
