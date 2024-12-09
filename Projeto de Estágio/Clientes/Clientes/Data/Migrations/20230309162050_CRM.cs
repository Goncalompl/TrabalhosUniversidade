using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class CRM : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {


           
                

           

            migrationBuilder.AddColumn<int>(
                name: "QuenteId",
                table: "Leads",
                type: "int",
                nullable: false,
                defaultValue: 0);

          

            

            migrationBuilder.AddColumn<string>(
                name: "EstadoLeadNome",
                table: "Estados",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "");

            migrationBuilder.CreateIndex(
                name: "IX_Leads_QuenteId",
                table: "Leads",
                column: "QuenteId");

           
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Leads_Estados_QuenteId",
                table: "Leads");

            migrationBuilder.DropIndex(
                name: "IX_Leads_QuenteId",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "DataPrimeroC",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "Detalhes",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "QuenteId",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "Responsavel",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "SetorAtividade",
                table: "Leads");

            migrationBuilder.DropColumn(
                name: "EstadoLeadNome",
                table: "Estados");

            migrationBuilder.AddColumn<bool>(
                name: "EstadoLead",
                table: "Leads",
                type: "bit",
                nullable: false,
                defaultValue: false);
        }
    }
}
