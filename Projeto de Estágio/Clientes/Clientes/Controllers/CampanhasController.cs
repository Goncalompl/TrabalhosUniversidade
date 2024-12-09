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
    public class CampanhasController : Controller
    {
        private readonly ApplicationDbContext _context;

        public CampanhasController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: Campanhas
        public async Task<IActionResult> Index(string TextoAPesquisar,int Plataforma, int Data, int Investimento, int Interacao, int Leads, DateTime data1, DateTime data2, int datanova=1)

        {

            if (TextoAPesquisar != null)
            {
                return View(await _context.Campanhas.Where(c => c.Plataforma.Contains(TextoAPesquisar)).ToListAsync());
            }

            if (Plataforma == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Plataforma).ToListAsync());
            }

            if (Plataforma == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Plataforma).ToListAsync());
            }

            if (Data == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Data).ToListAsync());
            }

            if (Data == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Data).ToListAsync());
            }

            if (Investimento == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Investimento).ToListAsync());
            }

            if (Investimento == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Investimento).ToListAsync());
            }

            if (Interacao == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Interacao).ToListAsync());
            }

            if (Interacao == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Interacao).ToListAsync());
            }
            if (Leads == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Leads).ToListAsync());
            }

            if (Leads == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Leads).ToListAsync());
            }
            if (datanova == 1)
            {

                return View(await _context.Campanhas.ToListAsync());
            }

            ViewData["ClienteCamID"] = new SelectList(_context.Clientes.OrderBy(c => c.Nome), "Id", "Nome");
            return View(await _context.Campanhas.Where(c => c.Data > DateTime.Now && c.Data >= data1 && c.Data <= data2).OrderBy(c => c.Plataforma).ToListAsync());
        }

        // GET: Campanhas/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Campanhas == null)
            {
                return NotFound();
            }

            var campanha = await _context.Campanhas
                .FirstOrDefaultAsync(m => m.CampanhaId == id);
            if (campanha == null)
            {
                return NotFound();
            }

            ViewData["ClienteCamID"] = new SelectList(_context.Clientes, "Id", "Nome");

            return View(campanha);
        }

        // GET: Campanhas/Create
        public IActionResult Create()
        {
            //var dataList = _context.Clientes;
            

            //var selectList = new SelectList(dataList, "Id", "Nome");

            //ViewData["ClienteCamID"] = selectList;
            ViewData["ClienteCamID"] = new SelectList(_context.Clientes, "Id", "Nome");
            return View();
        }

        // POST: Campanhas/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("CampanhaId,Plataforma,Data,Investimento,Responsavel,Interacao,Leads,Descricao,ClienteCampanha")] Campanha campanha)
        {
            
            
            if (ModelState.IsValid)
            {
                campanha.Data= campanha.Data;
                _context.Add(campanha);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            
            
            return View(campanha);
        }

        // GET: Campanhas/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Campanhas == null)
            {
                return NotFound();
            }

            var campanha = await _context.Campanhas.FindAsync(id);
            if (campanha == null)
            {
                return NotFound();
            }

            ViewData["ClienteCamID"] = new SelectList(_context.Clientes, "Id", "Nome");
            return View(campanha);
        }

        // POST: Campanhas/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("CampanhaId,Plataforma,Data,Responsavel,Investimento,Interacao,Leads,Descricao,Online,ClienteCampanha")] Campanha campanha)
        {

           
            if (id != campanha.CampanhaId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(campanha);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CampanhaExists(campanha.CampanhaId))
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
            return View(campanha);
        }

        // GET: Campanhas/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Campanhas == null)
            {
                return NotFound();
            }

            var campanha = await _context.Campanhas
                .FirstOrDefaultAsync(m => m.CampanhaId == id);
            if (campanha == null)
            {
                return NotFound();
            }

            return View(campanha);
        }

        // POST: Campanhas/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Campanhas == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Campanha'  is null.");
            }
            var campanha = await _context.Campanhas.FindAsync(id);
            if (campanha != null)
            {
                _context.Campanhas.Remove(campanha);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CampanhaExists(int id)
        {
          return _context.Campanhas.Any(e => e.CampanhaId == id);
        }


        public async Task<IActionResult> ListaCampanhas(string TextoAPesquisar, int Plataforma, int Data, int Investimento, int Interacao, int Leads, DateTime data1, DateTime data2, int Ativa, int datanova = 1)

        {
            ViewData["ClienteCamID"] = new SelectList(_context.Clientes, "Id", "Nome");

            if (TextoAPesquisar != null)
            {
                return View(await _context.Campanhas.Where(c => c.Plataforma.Contains(TextoAPesquisar)).ToListAsync());
            }

            if (Plataforma == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Plataforma).ToListAsync());
            }

            if (Plataforma == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Plataforma).ToListAsync());
            }

            if (Ativa == 1)
            {
                return View(await _context.Campanhas.Where(c => c.Online == true).ToListAsync());
            }

            if (Ativa == 2)
            {

                return View(await _context.Campanhas.Where(c => c.Online == false).ToListAsync());
            }

            if (Data == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Data).ToListAsync());
            }

            if (Data == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Data).ToListAsync());
            }

            if (Investimento == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Investimento).ToListAsync());
            }

            if (Investimento == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Investimento).ToListAsync());
            }

            if (Interacao == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Interacao).ToListAsync());
            }

            if (Interacao == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Interacao).ToListAsync());
            }
            if (Leads == 1)
            {
                return View(await _context.Campanhas.OrderBy(c => c.Leads).ToListAsync());
            }

            if (Leads == 2)
            {

                return View(await _context.Campanhas.OrderByDescending(c => c.Leads).ToListAsync());
            }
            if (datanova == 1)
            {

                return View(await _context.Campanhas.OrderBy(c => c.Plataforma).ToListAsync());
            }


            return View(await _context.Campanhas.Where(c => c.Data >= data1 && c.Data <= data2).ToListAsync());
        }




    }
}
