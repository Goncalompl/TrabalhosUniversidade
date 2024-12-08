using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using TrabalhoFinalRentCar.Models;

namespace TrabalhoFinalRentCar.Data
{
    public class ApplicationDbContext : IdentityDbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        public DbSet<Aluguer> AluguerAnual { get; set; }
        public DbSet<Aluguer> Alugueres  { get; set; }
        public DbSet<Veiculo> Veiculo { get; set; }
        public DbSet<Empresa> Empresas { get; set; }
        public DbSet<Review> Reviews { get; set; }
      
       // public IEnumerable<object> Checklist { get; internal set; }
    }
}