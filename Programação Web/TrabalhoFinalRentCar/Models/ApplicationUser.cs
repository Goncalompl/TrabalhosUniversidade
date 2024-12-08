using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using System.Xml.Linq;
using Microsoft.AspNetCore.Identity;


namespace TrabalhoFinalRentCar.Models
{
    public class ApplicationUser : IdentityUser
    {
        [Display(Name = "Primeiro Nome")]
        public string PrimeiroNome { get; set; }

        [Display(Name = "Último Nome")]
        public string UltimoNome { get; set; }

        [Display(Name = "Data de Nascimento")]
        public DateTime DataNascimento { get; set; }
        public int NIF { get; set; }

        [Display(Name = "O meu Avatar")]
        public byte[]? Avatar { get; set; }
        public ICollection<Aluguer> Alugueres { get; set; }

    }
}
