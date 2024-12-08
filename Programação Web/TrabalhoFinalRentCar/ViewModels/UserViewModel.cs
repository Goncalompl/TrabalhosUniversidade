using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;
using System.Xml.Linq;

namespace TrabalhoFinalRentCar.ViewModels
{
    public class UserViewModel
    {
        public string Id { get; set; }

        [Display(Name = "Email")]
        public string Email { get; set; }

        [Display(Name = "Nome")]
        public string PrimeiroNome { get; set; }
  

        [Display(Name = "Data de Nascimento")]
        public DateTime DataNascimento { get; set; }
        public int NIF { get; set; }

        [Display(Name = "Número de Telefone")]
        public string PhoneNumber { get; set; }

        [Display(Name = "O meu Avatar")]
        public byte[]? Avatar { get; set; }
    }
}
