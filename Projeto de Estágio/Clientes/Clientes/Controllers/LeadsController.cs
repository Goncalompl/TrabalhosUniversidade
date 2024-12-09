using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Clientes.Data;
using Clientes.Models;

namespace Clientes.Controllers
{
    public class LeadsController : Controller
    {
        private readonly ApplicationDbContext _context;

        public LeadsController(ApplicationDbContext context)
        {
            _context = context;
        }

        //GET:Leads
        public async Task<IActionResult> Index(string TextoAPesquisar, int Quente=3)
        {
            if (TextoAPesquisar != null && Quente==3)
            {

                return View(await _context.Leads.Where(c => c.Nome.Contains(TextoAPesquisar)).ToListAsync());
            }
            var applicationDbContext = _context.Leads.Include(a => a.Quente);
            ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome");
            if(Quente == 1)
            {

                return View(await applicationDbContext.Where(c => c.Quente.EstadoLeadNome == "Quente").ToListAsync());

            }
            if(Quente == 2)
            {

                return View(await applicationDbContext.Where(c => c.Quente.EstadoLeadNome == "Frio").ToListAsync());

            }
            return View(await applicationDbContext.Where(c => c.Quente.EstadoLeadNome == "Quente" || c.Quente.EstadoLeadNome == "Frio").OrderBy(c => c.Nome).ToListAsync());
        }

        public async Task<IActionResult> Tabela(string TextoAPesquisar, int Nome, int Responsavel, int Email, int Telefone, int AFazer, int Estado, DateTime data1, DateTime data2, int datanova = 1)

        {
            ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome");
            var applicationDbContext = _context.Leads.Include(a => a.Quente);

            if (TextoAPesquisar != null)
            {
                return View(await applicationDbContext.Where(c => c.Nome.Contains(TextoAPesquisar)).ToListAsync());
            }

            if (Nome == 1)
            {
                return View(await applicationDbContext.OrderBy(c => c.Nome).ToListAsync());
            }

            if (Nome == 2)
            {

                return View(await applicationDbContext.OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (Responsavel == 1)
            {
                return View(await applicationDbContext.OrderBy(c => c.Responsavel).ToListAsync());
            }

            if (Responsavel == 2)
            {

                return View(await applicationDbContext.OrderByDescending(c => c.Responsavel).ToListAsync());
            }

            if (Email == 1)
            {
                return View(await applicationDbContext.OrderBy(c => c.Email).ToListAsync());
            }

            if (Email == 2)
            {

                return View(await applicationDbContext.OrderByDescending(c => c.Email).ToListAsync());
            }

            if (Telefone== 1)
            {
                return View(await applicationDbContext.OrderBy(c => c.Telefone).ToListAsync());
            }

            if (Telefone == 2)
            {

                return View(await applicationDbContext.OrderByDescending(c => c.Telefone).ToListAsync());
            }
            if (AFazer == 1)
            {
                return View(await applicationDbContext.OrderBy(c => c.AFazer).ToListAsync());
            }

            if (AFazer == 2)
            {

                return View(await applicationDbContext.OrderByDescending(c => c.AFazer).ToListAsync());
            }
            if (Estado == 1)
            {
                return View(await applicationDbContext.OrderBy(c => c.Quente.EstadoLeadNome).ToListAsync());
            }

            if (Estado == 2)
            {

                return View(await applicationDbContext.OrderByDescending(c => c.Quente.EstadoLeadNome).ToListAsync());
            }
            if (datanova == 1)
            {

                return View(await applicationDbContext.OrderBy(c => c.Nome).ToListAsync());
            }

            ViewData["ClienteCamID"] = new SelectList(_context.Clientes, "Id", "Nome");
            return View(await _context.Leads.OrderBy(c => c.Nome).ToListAsync());
        }


        // GET: Leads/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Leads == null)
            {
                return NotFound();
            }
             ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome");


            var leads = await _context.Leads.Include(a => a.Quente)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (leads == null)
            {
                return NotFound();
            }

            return View(leads);
        }

        // GET: Leads/Create
        public IActionResult Create()
        {
            ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome");

            return View();
        }

        // POST: Leads/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Nome,Responsavel,PrimeiroContacto,DataPrimeiroC,AFazer,EstadoLead,Reuniao,Proposta,Resultados,Observacoes,Detalhes,Email,Telefone,SetorAtividade,QuenteId")] Leads leads)
        {
            ModelState.Remove(nameof(leads.Quente));

            if (ModelState.IsValid)
            {
                _context.Add(leads);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome", leads.QuenteId);
            return View(leads);
        }

        // GET: Leads/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Leads == null)
            {
                return NotFound();
            }

            var leads = await _context.Leads.FindAsync(id);
            if (leads == null)
            {
                return NotFound();
            }
            ViewData["QuenteId"] = new SelectList(_context.Estados/*.Where(c => c.EstadoLeadNome.Equals("Quente") || c.EstadoLeadNome.Equals("Frio"))*/, "EstadoId", "EstadoLeadNome");
            return View(leads);
        }

        // POST: Leads/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Nome,Responsavel,PrimeiroContacto,DataPrimeiroC,AFazer,EstadoLead,Reuniao,Proposta,Resultados,Observacoes,Detalhes,Email,Telefone,SetorAtividade,QuenteId")] Leads leads)
        {
            ModelState.Remove(nameof(leads.Quente));
            if (id != leads.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(leads);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!LeadsExists(leads.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome");
            return View(leads);
        }

        // GET: Leads/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Leads == null)
            {
                return NotFound();
            }

            var leads = await _context.Leads
                .FirstOrDefaultAsync(m => m.Id == id);
            if (leads == null)
            {
                return NotFound();
            }

            return View(leads);
        }

        // POST: Leads/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Leads == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Leads'  is null.");
            }
            var leads = await _context.Leads.FindAsync(id);
            if (leads != null)
            {
                _context.Leads.Remove(leads);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool LeadsExists(int id)
        {
          return _context.Leads.Any(e => e.Id == id);
        }

        public async Task<IActionResult> ListaClientesLeads(string TextoAPesquisar,int nome, int email, int telefone)
        {
            if (TextoAPesquisar != null)
            {
                return View(await _context.Leads.Where(c => c.Nome.Contains(TextoAPesquisar)).ToListAsync());
            }


            if (nome == 1)
            {
                return View(await _context.Leads.OrderBy(c => c.Nome).ToListAsync());
            }

            if (nome == 2)
            {

                return View(await _context.Leads.OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (email == 1)
            {
                return View(await _context.Leads.OrderBy(c => c.Email).ToListAsync());
            }

            if (email == 2)
            {
                return View(await _context.Leads.OrderByDescending(c => c.Email).ToListAsync());
            }

            if (telefone == 1)
            {
                return View(await _context.Leads.OrderBy(c => c.Telefone).ToListAsync());
            }

            if (telefone == 2)
            {

                return View(await _context.Leads.OrderByDescending(c => c.Telefone).ToListAsync());
            }

           

            return View(await _context.Leads.ToListAsync());
        }

        public async Task<IActionResult> LeadsConcluidas(string TextoAPesquisar,int nome, int primeiroC, int aFazer, int estado)
        {

            if (TextoAPesquisar != null)
            {

                return View(await _context.Leads.Where(c => c.Nome.Contains(TextoAPesquisar) && c.Quente.EstadoLeadNome == "Feito").OrderBy(c => c.Nome).ToListAsync());
            }

            if (nome == 1)
            {
                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderBy(c => c.Nome).ToListAsync());
            }

            if (nome == 2)
            {

                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (primeiroC == 1)
            {
                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderBy(c => c.PrimeiroContacto).ToListAsync());
            }

            if (primeiroC == 2)
            {
                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderByDescending(c => c.PrimeiroContacto).ToListAsync());
            }

            if (aFazer == 1)
            {
                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderBy(c => c.AFazer).ToListAsync());
            }

            if (aFazer == 2)
            {
                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderByDescending(c => c.AFazer).ToListAsync());
            }

            if (estado == 1)
            {
                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").ToListAsync());
            }

            if (estado == 2)
            {

                return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").ToListAsync());
            }



            return View(await _context.Leads.Where(c => c.Quente.EstadoLeadNome == "Feito").OrderBy(c => c.Nome).ToListAsync());
        }



        // GET: Leads/Edit/5
        public async Task<IActionResult> EditarEstadoLead(int? id)
        {
            if (id == null || _context.Leads == null)
            {
                return NotFound();
            }

            var leads = await _context.Leads.FindAsync(id);
            if (leads == null)
            {
                return NotFound();
            }
            ViewData["QuenteId"] = new SelectList(_context.Estados/*.Where(c => c.EstadoLeadNome.Equals("Quente") || c.EstadoLeadNome.Equals("Frio"))*/, "EstadoId", "EstadoLeadNome");
            return View(leads);
        }

        // POST: Leads/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> EditarEstadoLead(int id, [Bind("Id,Nome,Responsavel,PrimeiroContacto,DataPrimeiroC,AFazer,EstadoLead,Reuniao,Proposta,Resultados,Observacoes,Detalhes,Email,Telefone,SetorAtividade,QuenteId")] Leads leads)
        {
            ModelState.Remove(nameof(leads.Quente));
            if (id != leads.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(leads);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!LeadsExists(leads.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["QuenteId"] = new SelectList(_context.Estados, "EstadoId", "EstadoLeadNome");
            return View(leads);
        }


        public async Task<IActionResult> ClientesLeadsCards(string TextoAPesquisar, int nome, int email, int telefone)
        {
            if (TextoAPesquisar != null)
            {
                return View(await _context.Leads.Where(c => c.Nome.Contains(TextoAPesquisar)).ToListAsync());
            }


            if (nome == 1)
            {
                return View(await _context.Leads.OrderBy(c => c.Nome).ToListAsync());
            }

            if (nome == 2)
            {

                return View(await _context.Leads.OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (email == 1)
            {
                return View(await _context.Leads.OrderBy(c => c.Email).ToListAsync());
            }

            if (email == 2)
            {
                return View(await _context.Leads.OrderByDescending(c => c.Email).ToListAsync());
            }

            if (telefone == 1)
            {
                return View(await _context.Leads.OrderBy(c => c.Telefone).ToListAsync());
            }

            if (telefone == 2)
            {

                return View(await _context.Leads.OrderByDescending(c => c.Telefone).ToListAsync());
            }



            return View(await _context.Leads.OrderBy(c => c.Nome).ToListAsync());
        }





    }
}
