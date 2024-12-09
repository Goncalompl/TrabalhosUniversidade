using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Clientes.Data;
using Clientes.Models;
using Clientes.Data.Migrations;

namespace Clientes.Controllers
{
    public class AgendamentosController : Controller
    {
        private readonly ApplicationDbContext _context;

        public AgendamentosController(ApplicationDbContext context)
        {
            _context = context;
        }
        
        // GET: Agendamentos
        public async Task<IActionResult> Index(string TextoAPesquisar, int? data, int tipo, int estado, int nome, int recente, DateTime data1 , DateTime data2, int responsavel,int pesquisadata,int datanova =1)
        {
            
            if (TextoAPesquisar != null)
            {
                return View(await _context.Agendamentos.Where(c => ((c.Data > DateTime.Now || c.Estado.EstadoNome.Equals("Remarcar"))  && c.Cliente.Nome.Contains(TextoAPesquisar)) || ((c.Data > DateTime.Now || c.Estado.EstadoNome.Equals("Remarcar")) && c.Responsavel.Contains(TextoAPesquisar))).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());

            }



            //if (DataLevantamento != null)
            //{
            //    return View(await _context.Agendamentos.Where(c => c.Data.Equals(DataLevantamento) && c.Data > DateTime.Now).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            //}

            var applicationDbContext = _context.Agendamentos.Include(a => a.Cliente).Include(a => a.Estado).Include(a=>a.Lead);
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["LeadID"] = new SelectList(_context.Leads.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome");

            if (data == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Data).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (data == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderByDescending(c => c.Data).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (tipo == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Tipo).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (tipo == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderByDescending(c => c.Tipo).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (estado == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Estado.EstadoNome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (estado == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderByDescending(c => c.Estado.EstadoNome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (nome == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Cliente.Nome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (nome == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderByDescending(c => c.Cliente.Nome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (recente == 1)
            {

                return View(await _context.Agendamentos.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado").Where(c => c.Data < DateTime.Now.AddDays(5)).OrderByDescending(c => c.Cliente.Nome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }
            if (datanova == 1)
            {
                
                return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").ToListAsync());
            }
            if (responsavel == 1)
            {

               // return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Responsavel).ToListAsync());
                return View(await _context.Agendamentos.OrderBy(c => c.Responsavel).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());

            }
            if (responsavel == 2)
            {

                //return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderByDescending(c => c.Responsavel).ToListAsync());
                return View(await _context.Agendamentos.OrderByDescending(c => c.Responsavel).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }
            if (pesquisadata == 1 && datanova == 0)
            {

                return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" && c.Data >= data1 && c.Data <= data2 || c.Estado.EstadoNome == "Remarcar" && c.Data >= data1 && c.Data <= data2 /*&& c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar"*/).ToListAsync());

            }
            return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").ToListAsync());

            //return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" && c.Data >= data1 && c.Data <= data2 || c.Estado.EstadoNome == "Remarcar" && c.Data >= data1 && c.Data <= data2 /*&& c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar"*/).ToListAsync());
        }

        // GET: Agendamentos/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Agendamentos == null)
            {
                return NotFound();
            }

            var agendamento = await _context.Agendamentos
                .Include(a => a.Cliente)
                .Include(a => a.Estado)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (agendamento == null)
            {
                return NotFound();
            }
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome");

            return View(agendamento);
        }

        // GET: Agendamentos/Create
        public IActionResult Create()
        {
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome");
            return View();
        }

        // POST: Agendamentos/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,Data,Tipo,Responsavel,Detalhes,EstadoId,ClienteID")] Agendamento agendamento)
        {
            ModelState.Remove(nameof(agendamento.Cliente));
            ModelState.Remove(nameof(agendamento.Estado));
            
            if (ModelState.IsValid)
            {
                _context.Add(agendamento);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome", agendamento.ClienteID);
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome", agendamento.EstadoId);
            return View(agendamento);
        }


        //// GET: Agendamentos/Create
        public IActionResult CreateLeads()
        {
            ViewData["LeadID"] = new SelectList(_context.Leads.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome");
            return View();
        }

        // POST: Agendamentos/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> CreateLeads([Bind("Id,Data,Tipo,Responsavel,Detalhes,EstadoId,LeadID")] Agendamento agendamento)
        {
            ModelState.Remove(nameof(agendamento.Lead));
            ModelState.Remove(nameof(agendamento.Estado));

            if (ModelState.IsValid)
            {
                _context.Add(agendamento);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["LeadID"] = new SelectList(_context.Leads.OrderBy(c => c.Nome), "Id", "Nome", agendamento.LeadID);
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome", agendamento.EstadoId);
            return View(agendamento);
        }


        // GET: Agendamentos/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Agendamentos == null)
            {
                return NotFound();
            }

            var agendamento = await _context.Agendamentos.FindAsync(id);
            if (agendamento == null)
            {
                return NotFound();
            }
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome", agendamento.ClienteID);
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome", agendamento.EstadoId);
            return View(agendamento);
        }

        // POST: Agendamentos/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Data,Tipo,Responsavel,Detalhes,ClienteID,EstadoId")] Agendamento agendamento)
        {
            ModelState.Remove(nameof(agendamento.Cliente));
            ModelState.Remove(nameof(agendamento.Estado));
            if (id != agendamento.Id)
            {
                return NotFound();
            }
          
           

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(agendamento);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AgendamentoExists(agendamento.Id))
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
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome", agendamento.ClienteID);
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome", agendamento.EstadoId);
            return View(agendamento);
        }

        // GET: Agendamentos/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Agendamentos == null)
            {
                return NotFound();
            }

            var agendamento = await _context.Agendamentos
                .Include(a => a.Cliente)
                .Include(a => a.Estado)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (agendamento == null)
            {
                return NotFound();
            }

            return View(agendamento);
        }

        // POST: Agendamentos/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Agendamentos == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Agendamentos'  is null.");
            }
            var agendamento = await _context.Agendamentos.FindAsync(id);
            if (agendamento != null)
            {
                _context.Agendamentos.Remove(agendamento);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool AgendamentoExists(int id)
        {
          return _context.Agendamentos.Any(e => e.Id == id);
        }

        public async Task<IActionResult> Historico(string TextoAPesquisar, int? data, int tipo, int Estado, DateTime data1, DateTime data2,int Responsavel,int pesquisadata, int cliente,int datanova = 1)
        {
            var applicationDbContext = _context.Agendamentos.Include(a => a.Cliente).Include(a => a.Estado);

            ViewData["Cliente"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["Estado"] = new SelectList(_context.Estados, "EstadoId", "EstadoNome");

            if (TextoAPesquisar != null)
            {
                return View(await _context.Agendamentos.Where(c => c.Cliente.Nome.Contains(TextoAPesquisar) && c.Data < DateTime.Now).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (cliente == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderBy(c => c.Cliente.Nome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (cliente == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderByDescending(c => c.Cliente.Nome).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (data == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderBy(c => c.Data).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (data == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderByDescending(c => c.Data).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (tipo == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderBy(c => c.Tipo).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (tipo == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderByDescending(c => c.Tipo).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (Estado == 1)
            {
                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderBy(c => c.Estado).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            if (Estado == 2)
            {

                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderByDescending(c => c.Estado).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }
            if (datanova == 1)
            {

                return View(await applicationDbContext.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderBy(c => c.Cliente.Nome).ToListAsync());
            }
            if (Responsavel == 1)
            {

                return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Responsavel).ToListAsync());
            }
            if (Responsavel == 2)
            {

                return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderByDescending(c => c.Responsavel).ToListAsync());
            }

            if (pesquisadata == 1 && datanova == 0)
            {

                return View(await applicationDbContext.Where(c => c.Data >= data1 && c.Data <= data2 && c.Estado.EstadoNome != "Remarcar" && c.Data < DateTime.Now).OrderBy(c => c.Cliente.Nome).ToListAsync());
            }
            return View(await applicationDbContext.Where(c => c.Data < DateTime.Now || c.Estado.EstadoNome == "Realizado").OrderBy(c => c.Cliente.Nome).ToListAsync());
        }


        public async Task<IActionResult> ListaAgendamentos(string TextoAPesquisar, int datale, int? data, int tipo, int estado, int nome, int recente, DateTime data1, int temp1, DateTime data2, int datanova = 1)
        {
            

            if (TextoAPesquisar != null)
            {
                return View(await _context.Agendamentos.Where(c => ((c.Data > DateTime.Now || c.Estado.EstadoNome.Equals("Remarcar")) && c.Cliente.Nome.Equals(TextoAPesquisar)) || ((c.Data > DateTime.Now || c.Estado.EstadoNome.Equals("Remarcar")) && c.Responsavel.Equals(TextoAPesquisar))).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }


            if (recente == 1)
            {

                return View(await _context.Agendamentos.Where(c => c.Data < DateTime.Now.AddDays(5) && c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado").OrderBy(c => c.Data).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            }

            //if (DataLevantamento != null)
            //{
            //    return View(await _context.Agendamentos.Where(c => c.Data.Equals(DataLevantamento) && c.Data > DateTime.Now).Include(a => a.Cliente).Include(a => a.Estado).ToListAsync());
            //}

            var applicationDbContext = _context.Agendamentos.Include(a => a.Cliente).Include(a => a.Estado);
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome");
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome");

          

            return View(await applicationDbContext.Where(c => c.Data > DateTime.Now && c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar").OrderBy(c => c.Data).ToListAsync());
        }



        public async Task<IActionResult> EditarEstadoAgendamento(int? id)
        {
            if (id == null || _context.Agendamentos == null)
            {
                return NotFound();
            }

            var agendamento = await _context.Agendamentos.FindAsync(id);
            if (agendamento == null)
            {
                return NotFound();
            }
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome", agendamento.ClienteID);
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome", agendamento.EstadoId);
            ViewData["LeadID"] = new SelectList(_context.Leads.OrderBy(c => c.Nome), "Id", "Nome", agendamento.LeadID);
            return View(agendamento);
        }

        // POST: Agendamentos/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> EditarEstadoAgendamento(int id, [Bind("EstadoId,ClienteID")] Agendamento agendamento)
        {
            if (id != agendamento.Id)
            {
                return NotFound();
            }
            
            ModelState.Remove(nameof(agendamento.Estado)); 
            

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(agendamento);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AgendamentoExists(agendamento.Id))
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
            ViewData["ClienteID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome", agendamento.ClienteID);
            ViewData["EstadoId"] = new SelectList(_context.Estados.OrderBy(c => c.EstadoNome), "EstadoId", "EstadoNome", agendamento.EstadoId);
            ViewData["LeadID"] = new SelectList(_context.Leads.OrderBy(c => c.Nome), "Id", "Nome", agendamento.LeadID);
            return View(agendamento);
        }

    }
}
