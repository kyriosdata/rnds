using System;
using System.Collections.Generic;
using Hl7.Fhir.Model;
using Hl7.Fhir.Serialization;

namespace cliente_dotnet
{
    class Program
    {
        static void Main(string[] args)
        {
            Patient paciente = new Patient();
            var nome = new HumanName().WithGiven("João").AndFamily("da Silva");
            paciente.Name.Add(nome);

            paciente.Name = new List<HumanName>() { nome };

            var serializer = new FhirJsonSerializer(new SerializerSettings()
            {
                Pretty = true
            });

            var json = serializer.SerializeToString(paciente);
            Console.WriteLine(json);
        }
    }
}
