# Persona Agent

Persona Agent is a Spring Boot application that allows users to upload LinkedIn profiles as PDF files and automatically generates a chat interface.  
Anyone interested can ask questions about the employee, and an AI model provides intelligent answers based on the uploaded profile.

## Features

- Upload LinkedIn profiles in PDF format
- Generate a chat interface for each profile
- AI-powered answers to questions about the employee
- Secure configuration using environment variables for OpenAI API key
- Built with Spring Boot and ready for Docker deployment

## Getting Started

1. Set your OpenAI API key:

**Windows PowerShell:**
```powershell
$env:OPEN_API_KEY="sk-yourkey"
```

2. Configure application.yml:
```powershell
openai:
  apiKey: ${OPEN_API_KEY}
```

